drop database android;

CREATE DATABASE android;

CREATE TABLE `inventory` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `inventory`
  ADD PRIMARY KEY (`id`);


INSERT INTO `inventory` (`id`, `name`, `quantity`, `price`) VALUES
(10001, 'Laptop', 200, 10000),
(10003, 'Keyboard คีย์บอร์ด', 34, 2577),
(10004, 'จอ', 32, 50000);

