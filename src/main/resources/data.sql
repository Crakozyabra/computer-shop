INSERT INTO producer(name)
VALUES ('Producer A'),
       ('Producer B'),
       ('Producer C');

insert
into base_computer_part
(price, producer_id, quantity_on_stock, serial_number, capacity, dtype, id)
values (5, 1, 30, 'HARD1', 256.0, 'HardDisk', default)