/*
SQLyog Community v8.61 
MySQL - 5.1.41-community : Database - toko_retail
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`toko_retail` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `toko_retail`;

/*Table structure for table `t_detail_pembelian` */

DROP TABLE IF EXISTS `t_detail_pembelian`;

CREATE TABLE `t_detail_pembelian` (
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

/*Data for the table `t_detail_pembelian` */

/*Table structure for table `t_detail_penjualan` */

DROP TABLE IF EXISTS `t_detail_penjualan`;

CREATE TABLE `t_detail_penjualan` (
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

/*Data for the table `t_detail_penjualan` */

insert  into `t_detail_penjualan`(`id_detail_penjualan`,`id_produk`,`kuantitas`,`harga`,`sub_total`,`id_penjualan`) values ('13139456286251','SABUN01',5,'2300.00','11500.00','1313945628625'),('13139456286252','ROKOK03',2,'12000.00','24000.00','1313945628625');

/*Table structure for table `t_pembelian` */

DROP TABLE IF EXISTS `t_pembelian`;

CREATE TABLE `t_pembelian` (
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

/*Data for the table `t_pembelian` */

/*Table structure for table `t_penjualan` */

DROP TABLE IF EXISTS `t_penjualan`;

CREATE TABLE `t_penjualan` (
  `id_penjualan` varchar(16) NOT NULL,
  `tanggal` datetime NOT NULL,
  `total` decimal(19,2) NOT NULL,
  `id_user` varchar(15) NOT NULL,
  PRIMARY KEY (`id_penjualan`),
  KEY `id_user` (`id_user`),
  CONSTRAINT `t_penjualan_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `t_user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_penjualan` */

insert  into `t_penjualan`(`id_penjualan`,`tanggal`,`total`,`id_user`) values ('1313945628625','2011-08-21 23:58:54','35500.00','admin');

/*Table structure for table `t_produk` */

DROP TABLE IF EXISTS `t_produk`;

CREATE TABLE `t_produk` (
  `id_produk` varchar(30) NOT NULL,
  `harga_jual` decimal(19,2) NOT NULL,
  `harga_pokok` decimal(19,2) NOT NULL,
  `nama` varchar(90) NOT NULL,
  `stock` int(11) NOT NULL,
  PRIMARY KEY (`id_produk`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_produk` */

insert  into `t_produk`(`id_produk`,`harga_jual`,`harga_pokok`,`nama`,`stock`) values ('ROKOK01','11000.00','9000.00','Jarum 16',50),('ROKOK02','8000.00','8000.00','Jarum 12',50),('ROKOK03','12000.00','11000.00','MARLBORO LIGHT',48),('SABUN01','2300.00','1500.00','LUX',45),('SABUN02','2500.00','1600.00','SHINSUI',50);

/*Table structure for table `t_supplier` */

DROP TABLE IF EXISTS `t_supplier`;

CREATE TABLE `t_supplier` (
  `id_supplier` varchar(20) NOT NULL,
  `nama_supplier` varchar(50) NOT NULL,
  `alamat` text NOT NULL,
  `telepon` varchar(15) NOT NULL,
  PRIMARY KEY (`id_supplier`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_supplier` */

insert  into `t_supplier`(`id_supplier`,`nama_supplier`,`alamat`,`telepon`) values ('TOKO01','TOKO SEMBARANG','SEMARANG','0243555111'),('TOKO02','TOKO ADA AJA','SALATIGA','0298000111'),('TOKO03','TOKO BARU','KUDUS','029811111');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id_user` varchar(15) NOT NULL,
  `password` varchar(20) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `alamat` text NOT NULL,
  `telepon` varchar(15) NOT NULL,
  `role` enum('KASIR','ADMIN') NOT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_user` */

insert  into `t_user`(`id_user`,`password`,`nama`,`alamat`,`telepon`,`role`) values ('admin','admin','yulias kurniawan','semarang','081391550684','ADMIN'),('KASIR01','kasir','Budi','semarang','024123123','KASIR');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
