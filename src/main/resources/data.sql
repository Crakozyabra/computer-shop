INSERT INTO producer(name)
VALUES ('Producer A'), -- id=1
       ('Producer B'), -- id=2
       ('Producer C'); -- id=3

INSERT
INTO base_computer_part
(price, producer_id, quantity_on_stock, serial_number, capacity, dtype, id)
VALUES (1000, 1, 10, 'SERIAL_HDD_1', 128.0, 'Hdd', default), -- id=1
       (1000, 2, 20, 'SERIAL_HDD_2', 256.0, 'Hdd', default), -- id=2
       (1000, 3, 30, 'SERIAL_HDD_3', 512.0, 'Hdd', default); -- id=3

INSERT
INTO base_computer_part
(price, producer_id, quantity_on_stock, serial_number, laptop_size, dtype, id)
VALUES (1000, 1, 10, 'SERIAL_LAPTOP_1', 13, 'Laptop', default), -- id=4
       (1000, 2, 20, 'SERIAL_LAPTOP_2', 14, 'Laptop', default), -- id=5
       (1000, 3, 30, 'SERIAL_LAPTOP_3', 15, 'Laptop', default), -- id=6
       (1000, 1, 40, 'SERIAL_LAPTOP_4', 17, 'Laptop', default); -- id=7

INSERT
INTO base_computer_part
(price, producer_id, quantity_on_stock, serial_number, diagonal, dtype, id)
VALUES (1000, 1, 10, 'SERIAL_MONITOR_1', 24.0, 'Monitor', default), -- id=8
       (1000, 2, 20, 'SERIAL_MONITOR_2', 27.0, 'Monitor', default), -- id=9
       (1000, 3, 30, 'SERIAL_MONITOR_3', 32.0, 'Monitor', default); -- id=10

INSERT
INTO base_computer_part
(price, producer_id, quantity_on_stock, serial_number, form_factor, dtype, id)
VALUES (1000, 1, 10, 'SERIAL_DESKTOP_COMPUTER_1', 'DESKTOP', 'Desktop', default), -- id=11
       (1000, 2, 20, 'SERIAL_DESKTOP_COMPUTER_2', 'NETTOP', 'Desktop', default), -- id=12
       (1000, 3, 30, 'SERIAL_DESKTOP_COMPUTER_3', 'MONOBLOCK', 'Desktop', default); -- id=13

