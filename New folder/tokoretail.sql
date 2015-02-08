-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versi server:                 5.5.32 - MySQL Community Server (GPL)
-- OS Server:                    Win32
-- HeidiSQL Versi:               9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for alexanderry
CREATE DATABASE IF NOT EXISTS `alexanderry` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `alexanderry`;


-- Dumping structure for table alexanderry.t_detail_pembelian
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

-- Dumping data for table alexanderry.t_detail_pembelian: ~0 rows (approximately)
/*!40000 ALTER TABLE `t_detail_pembelian` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_detail_pembelian` ENABLE KEYS */;


-- Dumping structure for table alexanderry.t_detail_penjualan
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

-- Dumping data for table alexanderry.t_detail_penjualan: ~0 rows (approximately)
/*!40000 ALTER TABLE `t_detail_penjualan` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_detail_penjualan` ENABLE KEYS */;


-- Dumping structure for table alexanderry.t_member
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

-- Dumping data for table alexanderry.t_member: ~1 rows (approximately)
/*!40000 ALTER TABLE `t_member` DISABLE KEYS */;
INSERT INTO `t_member` (`id_member`, `nama_lengkap`, `nama_panggilan`, `tgl_lahir`, `alamat`, `telepon`, `pekerjaan`, `perusahaan`, `jabatan`, `favorite`, `twit_pinbb`, `testimoni`) VALUES
	('M-ALX1000', 'Alexandro Del Piero', 'Pie', '1988-01-06 23:05:29', 'italia', '085265471816', 'pesepakbola', 'Juventus', 'Stricker', 'alz', '5845554', 'alexanderry mantap');
/*!40000 ALTER TABLE `t_member` ENABLE KEYS */;


-- Dumping structure for table alexanderry.t_pembelian
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

-- Dumping data for table alexanderry.t_pembelian: ~0 rows (approximately)
/*!40000 ALTER TABLE `t_pembelian` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_pembelian` ENABLE KEYS */;


-- Dumping structure for table alexanderry.t_penjualan
CREATE TABLE IF NOT EXISTS `t_penjualan` (
  `id_penjualan` varchar(16) NOT NULL,
  `tanggal` datetime NOT NULL,
  `total` decimal(19,2) NOT NULL,
  `id_user` varchar(15) NOT NULL,
  `id_member` varchar(30) NOT NULL,
  PRIMARY KEY (`id_penjualan`),
  KEY `id_user` (`id_user`),
  KEY `id_member` (`id_member`),
  CONSTRAINT `t_penjualan_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `t_user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_penjualan_ibfk_3` FOREIGN KEY (`id_member`) REFERENCES `t_member` (`id_member`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table alexanderry.t_penjualan: ~0 rows (approximately)
/*!40000 ALTER TABLE `t_penjualan` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_penjualan` ENABLE KEYS */;


-- Dumping structure for table alexanderry.t_produk
CREATE TABLE IF NOT EXISTS `t_produk` (
  `id_produk` varchar(30) NOT NULL,
  `harga_jual` decimal(19,2) NOT NULL,
  `harga_pokok` decimal(19,2) NOT NULL,
  `nama` varchar(90) NOT NULL,
  `stock` int(11) NOT NULL,
  PRIMARY KEY (`id_produk`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table alexanderry.t_produk: ~1 rows (approximately)
/*!40000 ALTER TABLE `t_produk` DISABLE KEYS */;
INSERT INTO `t_produk` (`id_produk`, `harga_jual`, `harga_pokok`, `nama`, `stock`) VALUES
	('ALX1001', 120000.00, 80000.00, 'T-SHIRT HITAM ALEX', 12);
/*!40000 ALTER TABLE `t_produk` ENABLE KEYS */;


-- Dumping structure for table alexanderry.t_supplier
CREATE TABLE IF NOT EXISTS `t_supplier` (
  `id_supplier` varchar(20) NOT NULL,
  `nama_supplier` varchar(50) NOT NULL,
  `alamat` text NOT NULL,
  `telepon` varchar(15) NOT NULL,
  PRIMARY KEY (`id_supplier`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table alexanderry.t_supplier: ~0 rows (approximately)
/*!40000 ALTER TABLE `t_supplier` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_supplier` ENABLE KEYS */;


-- Dumping structure for table alexanderry.t_user
CREATE TABLE IF NOT EXISTS `t_user` (
  `id_user` varchar(15) NOT NULL,
  `password` varchar(20) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `alamat` text NOT NULL,
  `telepon` varchar(15) NOT NULL,
  `role` enum('KASIR','ADMIN') NOT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table alexanderry.t_user: ~1 rows (approximately)
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` (`id_user`, `password`, `nama`, `alamat`, `telepon`, `role`) VALUES
	('admin', 'admin', 'Alexanderry', 'Alexanderry.com', '0852', 'ADMIN');
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
