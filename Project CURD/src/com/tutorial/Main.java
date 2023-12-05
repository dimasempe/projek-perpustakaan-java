package com.tutorial;

/*Import java library*/
import java.io.IOException;
import java.util.Scanner;

/*Import CRUD library*/
import CRUD.Operasi;
import CRUD.Utility;

public class Main {
    public static void main(String[] args) throws IOException{

        Scanner terminalInput = new Scanner(System.in);
        String pilihanUser;
        boolean isLanjutkan = true;


        while (isLanjutkan) {
            Utility.clearScreen();
            System.out.println("Database Perpustakaan\n");
            System.out.println("1.\tLihat seluruh buku");
            System.out.println("2.\tCari data buku");
            System.out.println("3.\tTambah data buku");
            System.out.println("4.\tUbah data buku");
            System.out.println("5.\tHapus data buku");

            System.out.print("\n\nPilihan Anda : ");
            pilihanUser = terminalInput.next();

            switch (pilihanUser) {
                case "1":
                    System.out.println("\n=================");
                    System.out.println("LIST SELURUH BUKU");
                    System.out.println("=================");
                    /*Tampilkan Data*/
                    Operasi.tampilkanData();
                    break;
                case "2":
                    System.out.println("\n==============");
                    System.out.println("CARI DATA BUKU");
                    System.out.println("==============");
                    /*Cari Data*/
                    Operasi.cariData();
                    break;
                case "3":
                    System.out.println("\n================");
                    System.out.println("TAMBAH DATA BUKU");
                    System.out.println("================");
                    /*Tambah Data*/
                    Operasi.tambahData();
                    Operasi.tampilkanData();
                    break;
                case "4":
                    System.out.println("\n==============");
                    System.out.println("UBAH DATA BUKU");
                    System.out.println("==============");
                    /*Ubah Data*/
                    Operasi.updateData();
                    break;
                case "5":
                    System.out.println("\n===============");
                    System.out.println("HAPUS DATA BUKU");
                    System.out.println("===============");
                    /*Hapus Data*/
                    Operasi.deleteData();
                    break;
                default:
                    System.err.println("\nInput Anda tidak ditemukan\nSilakan pilih angka [1 - 5]");
            }
            isLanjutkan = CRUD.Utility.getYesorNo("Apakah Anda Ingin Melanjutkan?");

        }
    }
}
