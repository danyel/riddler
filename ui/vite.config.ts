import {UserConfigFn} from 'vite';
import {overrideVaadinConfig} from './vite.generated';
import {playwright} from '@vitest/browser-playwright';

const customConfig: UserConfigFn = (env) => {
    return {
        plugins: [
            {
                name: 'disable-vaadin-pwa-in-tests',
                configResolved(config) {
                    const pwaPluginIndex = config.plugins.findIndex(p => p.name === 'vaadin:inject-manifest-to-sw');
                    if (pwaPluginIndex !== -1) {
                        config.plugins.splice(pwaPluginIndex, 1);
                    }
                }
            }
        ],
        test: {
            // DO NOT use environment: 'browser' here anymore, as it conflicts with browser.enabled
            include: [
                'src/main/frontend/**/*.{test,spec}.{ts,tsx}',
                'frontend/**/*.{test,spec}.{ts,tsx}'
            ],
            browser: {
                enabled: true, // This is the ONLY configuration that should turn on browser testing
                provider: playwright({
                    launchOptions: {
                        slowMo: 50,
                        channel: 'chrome-beta',
                    },
                    actionTimeout: 5_000,
                }),
                instances: [
                    {browser: 'chromium'},
                    {
                        browser: 'firefox',
                        provider: playwright({
                            launchOptions: {
                                firefoxUserPrefs: {
                                    'browser.startup.homepage': 'https://example.com',
                                },
                            },
                        })
                    }
                ],
            },
        },
    };
};

export default overrideVaadinConfig(customConfig);
