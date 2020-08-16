const puppeteer = require('puppeteer');
const _ = require('lodash');
const log4js = require("./util/log.js");
const logger = log4js.getLogger("default");
const os = require('os');
const config = require('./config.js');

async function createBrowser(opts) {
    let browser;
    logger.debug(`${opts.launch}`)
    browser = await puppeteer.launch(opts.launch);
    logger.info("create browser end...")
    return browser;
}
 
async function getFullPageHeight(page) {
    const height = await page.evaluate(() => {
        const {body, documentElement} = document;
        return Math.max(
            body.scrollHeight,
            body.offsetHeight,
            documentElement.clientHeight,
            documentElement.scrollHeight,
            documentElement.offsetHeight
        );
    });
    return height;
}

async function pdf(_opts = {}) {
    let opts;
    let browser;
    let data;
    try {
        opts = getOptions(_opts);
        logger.debug(opts);
        browser = await createBrowser(opts);

        const page = await browser.newPage();
        page.setDefaultNavigationTimeout(120 * 1000);

        if (opts.emulateMediaType) {
            logger.info("Emulate media screen...");
            await page.emulateMediaType('screen');
        }

        if (opts.cookies && opts.cookies.length > 0) {
            logger.info('Setting cookies..');

            const client = await page.target().createCDPSession();

            await client.send('Network.enable');
            await client.send('Network.setCookies', {cookies: opts.cookies});
        }

        logger.debug(opts.url)
        logger.info('Goto url  ..' + `${opts.url}`);

        await page.goto(opts.url, opts.goto);

        if (opts.outputType === 'PDF') {
            logger.info("render pdf.....")
            if (opts.pdf.fullPage) {
                const height = await getFullPageHeight(page);
                opts.pdf.height = height;
            }
            data = await page.pdf(opts.pdf);
            logger.info("render pdf end");
        } else {
            throw new Error("Unsupported type");
        }
    } catch (err) {
        logger.error(err);
        throw err;
    } finally {
        logger.info("closing browser.....");
        if (browser != undefined && browser != null) {
            await browser.close();
        }
    }
    console.log(opts.output);
    return data;
}

function getOptions(_opts = {}) {
    //logger.debug(_opts);
    const opts = _.merge(config.DEFAULT_PARAM, _opts);

    if ((_.get(opts, 'pdf.width') && _.get(opts, 'pdf.height')) || _.get(opts, 'pdf.fullPage')) {
        opts.pdf.format = undefined;
    }

    if (_.get(opts, 'output')) {
        opts.pdf.path = opts.output;
    }

    let launchArgs;
    if ((_.get(opts, 'launch')) && _.get(opts.launch, 'args')) {
        launchArgs = opts.launch.args;
    }

    launchArgs.concat(['--export-tagged-pdf']);

    if (os.type() == 'Windows_NT') {
        opts.launch.ignoreDefaultArgs = ['--disable-extensions'];
    } else {
        launchArgs.concat(['--no-sandbox', '--disable-setuid-sandbox']);
    }

    opts.launch.args = launchArgs;

    return opts;
}


const args = process.argv.splice(1);
logger.debug("args: " + args[1]);
const str = '\'';
const opts = JSON.parse(args[1].replace(new RegExp(str, 'gm'), '\"'));
logger.debug("opts:  " + opts);
return pdf(opts);
