CREATE DATABASE IF NOT EXISTS shopping_cart_localization
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE shopping_cart_localization;

CREATE TABLE IF NOT EXISTS cart_records (
                                            id INT AUTO_INCREMENT PRIMARY KEY,
                                            total_items INT NOT NULL,
                                            total_cost DOUBLE NOT NULL,
                                            language VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS cart_items (
                                          id INT AUTO_INCREMENT PRIMARY KEY,
                                          cart_record_id INT,
                                          item_number INT NOT NULL,
                                          price DOUBLE NOT NULL,
                                          quantity INT NOT NULL,
                                          subtotal DOUBLE NOT NULL,
                                          FOREIGN KEY (cart_record_id) REFERENCES cart_records(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS localization_strings (
                                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                                    `key` VARCHAR(100) NOT NULL,
    value VARCHAR(255) NOT NULL,
    language VARCHAR(10) NOT NULL
    );

USE shopping_cart_localization;

-- ── English (EN) ──────────────────────────────────────────────────────────────
INSERT INTO localization_strings (`key`, value, language) VALUES
                                                              ('confirm.language',      'Confirm Language',              'EN'),
                                                              ('enter.num.items',       'Enter number of items:',        'EN'),
                                                              ('num.items.prompt',      'Number of items',               'EN'),
                                                              ('enter.items',           'Enter Items',                   'EN'),
                                                              ('calculate.total',       'Calculate Total',               'EN'),
                                                              ('total.cost',            'Total:',                        'EN'),
                                                              ('item.header',           'Item',                          'EN'),
                                                              ('price.header',          'Price',                         'EN'),
                                                              ('quantity.header',       'Qty',                           'EN'),
                                                              ('item.label',            'Item',                          'EN'),
                                                              ('price.prompt',          'Price',                         'EN'),
                                                              ('quantity.prompt',       'Qty',                           'EN'),
                                                              ('error.invalid.number',  'Please enter a valid positive number.', 'EN'),
                                                              ('error.no.items',        'Please enter items first.',     'EN'),
                                                              ('error.invalid.item',    'Invalid price or quantity for item', 'EN');

-- ── Finnish (FI) ──────────────────────────────────────────────────────────────
INSERT INTO localization_strings (`key`, value, language) VALUES
                                                              ('confirm.language',      'Vahvista kieli',                          'FI'),
                                                              ('enter.num.items',       'Syötä ostettavien tuotteiden määrä:',     'FI'),
                                                              ('num.items.prompt',      'Tuotteiden määrä',                        'FI'),
                                                              ('enter.items',           'Syötä tuotteet',                          'FI'),
                                                              ('calculate.total',       'Laske kokonaishinta',                     'FI'),
                                                              ('total.cost',            'Kokonaishinta:',                          'FI'),
                                                              ('item.header',           'Tuote',                                   'FI'),
                                                              ('price.header',          'Hinta',                                   'FI'),
                                                              ('quantity.header',       'Määrä',                                   'FI'),
                                                              ('item.label',            'Tuote',                                   'FI'),
                                                              ('price.prompt',          'Hinta',                                   'FI'),
                                                              ('quantity.prompt',       'Määrä',                                   'FI'),
                                                              ('error.invalid.number',  'Anna kelvollinen positiivinen luku.',      'FI'),
                                                              ('error.no.items',        'Syötä ensin tuotteet.',                   'FI'),
                                                              ('error.invalid.item',    'Virheellinen hinta tai määrä tuotteelle', 'FI');

-- ── Swedish (SV) ──────────────────────────────────────────────────────────────
INSERT INTO localization_strings (`key`, value, language) VALUES
                                                              ('confirm.language',      'Bekräfta språk',                          'SV'),
                                                              ('enter.num.items',       'Ange antalet varor att köpa:',            'SV'),
                                                              ('num.items.prompt',      'Antal varor',                             'SV'),
                                                              ('enter.items',           'Ange varor',                              'SV'),
                                                              ('calculate.total',       'Beräkna totalkostnad',                    'SV'),
                                                              ('total.cost',            'Total kostnad:',                          'SV'),
                                                              ('item.header',           'Vara',                                    'SV'),
                                                              ('price.header',          'Pris',                                    'SV'),
                                                              ('quantity.header',       'Antal',                                   'SV'),
                                                              ('item.label',            'Vara',                                    'SV'),
                                                              ('price.prompt',          'Pris',                                    'SV'),
                                                              ('quantity.prompt',       'Antal',                                   'SV'),
                                                              ('error.invalid.number',  'Ange ett giltigt positivt nummer.',       'SV'),
                                                              ('error.no.items',        'Ange varor först.',                       'SV'),
                                                              ('error.invalid.item',    'Ogiltigt pris eller antal för vara',      'SV');

-- ── Japanese (JP) ─────────────────────────────────────────────────────────────
INSERT INTO localization_strings (`key`, value, language) VALUES
                                                              ('confirm.language',      '言語を確認',                      'JP'),
                                                              ('enter.num.items',       '購入する商品の数を入力してください：', 'JP'),
                                                              ('num.items.prompt',      '商品数',                          'JP'),
                                                              ('enter.items',           '商品を入力',                      'JP'),
                                                              ('calculate.total',       '合計金額を計算',                   'JP'),
                                                              ('total.cost',            '合計金額：',                      'JP'),
                                                              ('item.header',           '商品',                            'JP'),
                                                              ('price.header',          '価格',                            'JP'),
                                                              ('quantity.header',       '数量',                            'JP'),
                                                              ('item.label',            '商品',                            'JP'),
                                                              ('price.prompt',          '価格',                            'JP'),
                                                              ('quantity.prompt',       '数量',                            'JP'),
                                                              ('error.invalid.number',  '有効な正の数値を入力してください。',  'JP'),
                                                              ('error.no.items',        '先に商品を入力してください。',       'JP'),
                                                              ('error.invalid.item',    '商品の価格または数量が無効です',     'JP');

-- ── Arabic (AR) ───────────────────────────────────────────────────────────────
INSERT INTO localization_strings (`key`, value, language) VALUES
                                                              ('confirm.language',      'تأكيد اللغة',                    'AR'),
                                                              ('enter.num.items',       'أدخل عدد العناصر:',              'AR'),
                                                              ('num.items.prompt',      'عدد العناصر',                    'AR'),
                                                              ('enter.items',           'إدخال العناصر',                  'AR'),
                                                              ('calculate.total',       'حساب التكلفة الإجمالية',          'AR'),
                                                              ('total.cost',            'التكلفة الإجمالية:',              'AR'),
                                                              ('item.header',           'عنصر',                           'AR'),
                                                              ('price.header',          'السعر',                          'AR'),
                                                              ('quantity.header',       'الكمية',                         'AR'),
                                                              ('item.label',            'عنصر',                           'AR'),
                                                              ('price.prompt',          'السعر',                          'AR'),
                                                              ('quantity.prompt',       'الكمية',                         'AR'),
                                                              ('error.invalid.number',  'الرجاء إدخال عدد صحيح موجب.',    'AR'),
                                                              ('error.no.items',        'الرجاء إدخال العناصر أولاً.',    'AR'),
                                                              ('error.invalid.item',    'سعر أو كمية غير صحيحة للعنصر',  'AR');
