const puppeteer = require('puppeteer');
// const _ = require('lodash');
// const config = require('./config');
// const logger = require('./util/logger')(__filename);


const args = process.argv.splice(1)
// console.log(args)
opts = JSON.parse(args[1])
// console.log(opts)
// console.log(opts.url)
console.log(opts.url)
const pdf = (
    async() => {
        const browser = await puppeteer.launch();
        const page = await browser.newPage();

        //await page.pdf({path: opts.output, format: opts.pdf.format});
        try{
                console.log("页面跳转")
                await page.goto(opts.url, {waitUntil: 'networkidle0',
                        timeout: 5 * 60 * 1000});
                console.log
                ("生成PDF")
                const pdfFile = await page.pdf({
                        path: opts.output,
                        format: "A1",
                        printBackground: true,
                        displayHeaderFooter: true,
                        //  默认为 false，它将缩放内容以适合纸张大小
                        preferCSSPageSize: true,
                        margin: {
                                bottom: 69,
                                top: 57
                        }
                });
                console.log("生成PDF.....")
                await page.waitFor(opts.waitFor);
                console.log("生成成功")
        }catch(e){
                console.log(e);
        }finally {
                if(browser){
                        await browser.close();
                }

        }
    }
)()