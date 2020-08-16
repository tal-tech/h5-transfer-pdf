const config = {
    DEFAULT_PARAM: {
        cookies: [],
        scrollPage: false,
        emulateMediaType: false,
        ignoreHttpsErrors: true,
        viewport: {
            width: 1600,
            height: 1200
        },
        goto: {
            waitUntil: 'networkidle0',
            timeout: 60 * 1000
        },
        outputType: 'pdf',
        pdf: {
            format: 'A4',
            printBackground: true
        },
        screenshot: {
            type: 'png',
            fullPage: true
        },
        failEarly: false,
        launch: {
            ignoreTTPSErrors: false,
            args: [
                '--no-sandbox',
                '--disable-setuid-sandbox',
                '–disable-gpu',
                '–disable-dev-shm-usage',
                '–no-first-run',
                '–no-zygote',
                '–single-process',
            ]
        }
    }
};

module.exports = config;