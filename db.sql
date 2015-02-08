-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versi server:                 5.5.25a - MySQL Community Server (GPL)
-- OS Server:                    Win32
-- HeidiSQL Versi:               8.0.0.4396
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for toko_retail
DROP DATABASE IF EXISTS `toko_retail`;
CREATE DATABASE IF NOT EXISTS `toko_retail` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `toko_retail`;


-- Dumping structure for table toko_retail.t_detail_pembelian
DROP TABLE IF EXISTS `t_detail_pembelian`;
CREATE TABLE IF NOT EXISTS `t_detail_pembelian` (
  `id_detail_pembelian` varchar(19) NOT NULL,
  `id_produk` varchar(30) NOT NULL,
  `kuantitas` int(11) NOT NULL,
  `harga` decimal(19,2) NOT NULL,
  `sub_total` decimal(19,2) DEFAULT NULL,
  `id_pembelian` varchar(15) NOT NULL,
  PRIMARY KEY (`id_detail_pembelian`),
  KEY `id_produk` (`id_produk`),
  KEY `id_pembelian` (`id_pembelian`),
  CONSTRAINT `t_detail_pembelian_ibfk_1` FOREIGN KEY (`id_produk`) REFERENCES `t_produk` (`id_produk`),
  CONSTRAINT `t_detail_pembelian_ibfk_2` FOREIGN KEY (`id_pembelian`) REFERENCES `t_pembelian` (`id_pembelian`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table toko_retail.t_detail_pembelian: ~0 rows (approximately)
/*!40000 ALTER TABLE `t_detail_pembelian` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_detail_pembelian` ENABLE KEYS */;


-- Dumping structure for table toko_retail.t_detail_penjualan
DROP TABLE IF EXISTS `t_detail_penjualan`;
CREATE TABLE IF NOT EXISTS `t_detail_penjualan` (
  `id_detail_penjualan` varchar(19) NOT NULL,
  `id_produk` varchar(30) NOT NULL,
  `kuantitas` int(11) NOT NULL,
  `harga` decimal(19,2) NOT NULL,
  `sub_total` decimal(19,2) DEFAULT NULL,
  `id_penjualan` varchar(16) NOT NULL,
  PRIMARY KEY (`id_detail_penjualan`),
  KEY `id_produk` (`id_produk`),
  KEY `id_penjualan` (`id_penjualan`),
  CONSTRAINT `t_detail_penjualan_ibfk_1` FOREIGN KEY (`id_produk`) REFERENCES `t_produk` (`id_produk`),
  CONSTRAINT `t_detail_penjualan_ibfk_2` FOREIGN KEY (`id_penjualan`) REFERENCES `t_penjualan` (`id_penjualan`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table toko_retail.t_detail_penjualan: ~15 rows (approximately)
/*!40000 ALTER TABLE `t_detail_penjualan` DISABLE KEYS */;
INSERT INTO `t_detail_penjualan` (`id_detail_penjualan`, `id_produk`, `kuantitas`, `harga`, `sub_total`, `id_penjualan`) VALUES
	('020820130227441', 'ROKOK02', 1, 8000.00, 8000.00, '02082013022744'),
	('020820130227442', 'SABUN01', 1, 2300.00, 2300.00, '02082013022744'),
	('020820130243351', 'ROKOK01', 1, 11000.00, 11000.00, '02082013024335'),
	('020820130243352', 'SABUN01', 1, 2300.00, 2300.00, '02082013024335'),
	('020820130257091', 'ROKOK01', 1, 11000.00, 11000.00, '02082013025709'),
	('020820130257092', 'ROKOK02', 1, 8000.00, 8000.00, '02082013025709'),
	('020820130257093', 'SABUN01', 1, 2300.00, 2300.00, '02082013025709'),
	('020820130301341', 'ROKOK01', 1, 11000.00, 11000.00, '02082013030134'),
	('020820130308451', 'ROKOK01', 1, 11000.00, 11000.00, '02082013030845'),
	('020820130311351', 'ROKOK01', 1, 11000.00, 11000.00, '02082013031135'),
	('020820130325221', 'ROKOK01', 1, 11000.00, 11000.00, '02082013032522'),
	('020820130331281', 'ROKOK01', 1, 11000.00, 11000.00, '02082013033128'),
	('020820130334541', 'ROKOK01', 1, 11000.00, 11000.00, '02082013033454'),
	('020820130337551', 'ROKOK01', 1, 11000.00, 11000.00, '02082013033755'),
	('020820130340001', 'ROKOK01', 1, 11000.00, 11000.00, '02082013034000'),
	('020820130340002', 'ROKOK02', 1, 8000.00, 8000.00, '02082013034000'),
	('020820130416281', 'ROKOK01', 1, 11000.00, 11000.00, '02082013041628'),
	('13139456286251', 'SABUN01', 5, 2300.00, 11500.00, '1313945628625'),
	('13139456286252', 'ROKOK03', 2, 12000.00, 24000.00, '1313945628625'),
	('13750229758531', 'ROKOK02', 3, 8000.00, 24000.00, '1375022975853'),
	('13750229758532', 'SABUN02', 5, 2500.00, 12500.00, '1375022975853'),
	('13750236265021', 'SABUN01', 1, 2300.00, 2300.00, '1375023626502'),
	('13750236265022', 'ROKOK02', 1, 8000.00, 8000.00, '1375023626502'),
	('13750236754271', 'ROKOK01', 1, 11000.00, 11000.00, '1375023675427'),
	('13750236754272', 'SABUN01', 1, 2300.00, 2300.00, '1375023675427'),
	('13750397597551', 'SABUN01', 1, 2300.00, 2300.00, '1375039759755'),
	('13750400168651', 'SABUN01', 1, 2300.00, 2300.00, '1375040016865'),
	('13750401937601', 'SABUN01', 6, 2300.00, 13800.00, '1375040193760'),
	('13750408916311', 'SABUN01', 3, 2300.00, 6900.00, '1375040891631'),
	('290720130307531', 'SABUN01', 1, 2300.00, 2300.00, '29072013030753'),
	('310720130506231', 'ROKOK01', 1, 11000.00, 11000.00, '31072013050623'),
	('310720131233101', 'SABUN02', 1, 2500.00, 2500.00, '31072013123310');
/*!40000 ALTER TABLE `t_detail_penjualan` ENABLE KEYS */;


-- Dumping structure for table toko_retail.t_member
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE IF NOT EXISTS `t_member` (
  `id_member` varchar(30) NOT NULL,
  `nama_lengkap` varchar(30) NOT NULL,
  `nama_panggilan` varchar(20) NOT NULL,
  `tgl_lahir` datetime NOT NULL,
  `alamat` varchar(50) NOT NULL,
  `telepon` varchar(15) DEFAULT NULL,
  `pekerjaan` varchar(25) DEFAULT NULL,
  `perusahaan` varchar(40) DEFAULT NULL,
  `jabatan` varchar(30) DEFAULT NULL,
  `favorite` varchar(30) DEFAULT NULL,
  `twit_pinbb` varchar(30) DEFAULT NULL,
  `testimoni` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id_member`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table toko_retail.t_member: ~3 rows (approximately)
/*!40000 ALTER TABLE `t_member` DISABLE KEYS */;
INSERT INTO `t_member` (`id_member`, `nama_lengkap`, `nama_panggilan`, `tgl_lahir`, `alamat`, `telepon`, `pekerjaan`, `perusahaan`, `jabatan`, `favorite`, `twit_pinbb`, `testimoni`) VALUES
	('bbm:2a796c42377c6d2a2a796c42', 'afif', 'afif', '1992-06-18 00:00:00', 'jalan apay', '089789081234', 'dosen', 'stikom', 'manager', 'jaguar', '3424828', 'mantab'),
	('MEMBER001', 'Member Pertama', 'Tama', '1992-06-18 00:00:00', 'Jalan Tamtama', '085363964288', 'Programmer', 'Sendiri', 'Staff IT', 'meme', '287a1bbf', 'hahahh'),
	('ROKOK01', 'Karina Kumalasari S', 'Karin', '1990-06-15 00:00:00', 'Jalan Hasanuddin No. 83', '085274873363', 'Wiraswasta', 'Kopmil PING 2 U', 'Manager', 'FLOWER', '287A7BBF', 'Azzwars is the best partner');
/*!40000 ALTER TABLE `t_member` ENABLE KEYS */;


-- Dumping structure for table toko_retail.t_pembelian
DROP TABLE IF EXISTS `t_pembelian`;
CREATE TABLE IF NOT EXISTS `t_pembelian` (
  `id_pembelian` varchar(15) NOT NULL,
  `tanggal` datetime NOT NULL,
  `id_supplier` varchar(20) NOT NULL,
  `total` decimal(19,2) NOT NULL,
  `id_user` varchar(15) NOT NULL,
  PRIMARY KEY (`id_pembelian`),
  KEY `id_supplier` (`id_supplier`),
  KEY `id_user` (`id_user`),
  CONSTRAINT `t_pembelian_ibfk_2` FOREIGN KEY (`id_supplier`) REFERENCES `t_supplier` (`id_supplier`),
  CONSTRAINT `t_pembelian_ibfk_3` FOREIGN KEY (`id_user`) REFERENCES `t_user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table toko_retail.t_pembelian: ~0 rows (approximately)
/*!40000 ALTER TABLE `t_pembelian` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_pembelian` ENABLE KEYS */;


-- Dumping structure for table toko_retail.t_penjualan
DROP TABLE IF EXISTS `t_penjualan`;
CREATE TABLE IF NOT EXISTS `t_penjualan` (
  `id_penjualan` varchar(16) NOT NULL,
  `tanggal` datetime NOT NULL,
  `total` decimal(19,2) NOT NULL,
  `diskon` decimal(19,2) NOT NULL,
  `id_user` varchar(15) NOT NULL,
  `id_member` varchar(30) NOT NULL,
  PRIMARY KEY (`id_penjualan`),
  KEY `id_user` (`id_user`),
  KEY `id_member` (`id_member`),
  CONSTRAINT `t_penjualan_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `t_user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_penjualan_ibfk_3` FOREIGN KEY (`id_member`) REFERENCES `t_member` (`id_member`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table toko_retail.t_penjualan: ~3 rows (approximately)
/*!40000 ALTER TABLE `t_penjualan` DISABLE KEYS */;
INSERT INTO `t_penjualan` (`id_penjualan`, `tanggal`, `total`, `diskon`, `id_user`, `id_member`) VALUES
	('02082013022744', '2013-08-02 02:27:31', 10300.00, 10.00, 'admin', 'ROKOK01'),
	('02082013024335', '2013-08-02 02:43:27', 13300.00, 20.00, 'admin', 'ROKOK01'),
	('02082013025709', '2013-08-02 02:57:03', 21300.00, 0.00, 'admin', 'ROKOK01'),
	('02082013030134', '2013-08-02 03:01:29', 11000.00, 0.00, 'admin', 'ROKOK01'),
	('02082013030845', '2013-08-02 03:08:08', 11000.00, 0.00, 'admin', 'ROKOK01'),
	('02082013031135', '2013-08-02 03:11:28', 11000.00, 0.00, 'admin', 'ROKOK01'),
	('02082013032522', '2013-08-02 03:25:10', 11000.00, 0.00, 'admin', 'ROKOK01'),
	('02082013033128', '2013-08-02 03:31:21', 11000.00, 0.00, 'admin', 'ROKOK01'),
	('02082013033454', '2013-08-02 03:34:10', 11000.00, 0.00, 'admin', 'ROKOK01'),
	('02082013033755', '2013-08-02 03:37:48', 11000.00, 0.00, 'admin', 'ROKOK01'),
	('02082013034000', '2013-08-02 03:39:55', 19000.00, 0.00, 'admin', 'ROKOK01'),
	('02082013041628', '2013-08-02 04:15:54', 11000.00, 0.00, 'admin', 'ROKOK01'),
	('29072013030753', '2013-07-29 15:07:48', 2300.00, 0.00, 'admin', 'ROKOK01'),
	('31072013050623', '2013-07-31 05:05:39', 11000.00, 0.00, 'admin', 'ROKOK01'),
	('31072013123310', '2013-07-31 12:33:05', 2500.00, 0.00, 'admin', 'ROKOK01');
/*!40000 ALTER TABLE `t_penjualan` ENABLE KEYS */;


-- Dumping structure for table toko_retail.t_produk
DROP TABLE IF EXISTS `t_produk`;
CREATE TABLE IF NOT EXISTS `t_produk` (
  `id_produk` varchar(30) NOT NULL,
  `harga_jual` decimal(19,2) NOT NULL,
  `harga_pokok` decimal(19,2) NOT NULL,
  `nama` varchar(90) NOT NULL,
  `stock` int(11) NOT NULL,
  PRIMARY KEY (`id_produk`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table toko_retail.t_produk: ~6 rows (approximately)
/*!40000 ALTER TABLE `t_produk` DISABLE KEYS */;
INSERT INTO `t_produk` (`id_produk`, `harga_jual`, `harga_pokok`, `nama`, `stock`) VALUES
	('8999999718770', 85000.00, 8000.00, 'Rexona Men Ice Cool', 20),
	('ROKOK01', 11000.00, 9000.00, 'Jarum 16', 37),
	('ROKOK02', 8000.00, 8000.00, 'Jarum 12', 43),
	('ROKOK03', 12000.00, 11000.00, 'MARLBORO LIGHT', 48),
	('SABUN01', 2300.00, 1500.00, 'LUX', 28),
	('SABUN02', 2500.00, 1600.00, 'SHINSUI', 44);
/*!40000 ALTER TABLE `t_produk` ENABLE KEYS */;


-- Dumping structure for table toko_retail.t_supplier
DROP TABLE IF EXISTS `t_supplier`;
CREATE TABLE IF NOT EXISTS `t_supplier` (
  `id_supplier` varchar(20) NOT NULL,
  `nama_supplier` varchar(50) NOT NULL,
  `alamat` text NOT NULL,
  `telepon` varchar(15) NOT NULL,
  PRIMARY KEY (`id_supplier`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table toko_retail.t_supplier: ~3 rows (approximately)
/*!40000 ALTER TABLE `t_supplier` DISABLE KEYS */;
INSERT INTO `t_supplier` (`id_supplier`, `nama_supplier`, `alamat`, `telepon`) VALUES
	('TOKO01', 'TOKO SEMBARANG', 'SEMARANG', '0243555111'),
	('TOKO02', 'TOKO ADA AJA', 'SALATIGA', '0298000111'),
	('TOKO03', 'TOKO BARU', 'KUDUS', '029811111');
/*!40000 ALTER TABLE `t_supplier` ENABLE KEYS */;


-- Dumping structure for table toko_retail.t_user
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE IF NOT EXISTS `t_user` (
  `id_user` varchar(15) NOT NULL,
  `password` varchar(20) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `alamat` text NOT NULL,
  `telepon` varchar(15) NOT NULL,
  `role` enum('KASIR','ADMIN') NOT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table toko_retail.t_user: ~2 rows (approximately)
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` (`id_user`, `password`, `nama`, `alamat`, `telepon`, `role`) VALUES
	('admin', 'admin', 'Afiful Hadi', 'Bukit Tinggi', '081100000000', 'ADMIN'),
	('KASIR01', 'Budi', 'Sony', 'Pekanbaru', '0761000000', 'KASIR');
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
