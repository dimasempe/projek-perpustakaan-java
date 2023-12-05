package CRUD;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Operasi {
    public static void tampilkanData() throws IOException {
        FileReader fileInput;
        BufferedReader bufferInput;
        try {
            fileInput = new FileReader("database.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception e) {
            System.err.println("Database Tidak Ditemukan");
            System.err.println("Silakan Tambah Data Terlebih Dahulu");
            tambahData();
            return;
        }

        System.out.println("\n| No |\tTahun |\tPenulis             |\tPenerbit             |\tJudul Buku");
        System.out.println("-------------------------------------------------------------------------------");
        String data = bufferInput.readLine();
        int nomorData = 0;
        while(data != null) {
            nomorData++;
            StringTokenizer stringToken = new StringTokenizer(data, ",");

            stringToken.nextToken();
            System.out.printf("| %s  ", nomorData);
            System.out.printf("|\t%s  ", stringToken.nextToken());
            System.out.printf("|\t%-20s", stringToken.nextToken());
            System.out.printf("|\t%-20s ", stringToken.nextToken());
            System.out.printf("|\t%s", stringToken.nextToken());
            System.out.println("\n");

            data = bufferInput.readLine();
        }
        System.out.println("-------------------------------------------------------------------------------");
        bufferInput.close();
        fileInput.close();
    }

    public static void cariData() throws IOException {
        /*Membaca Databasse Ada atau Tidak*/
        try {
            File file = new File("database.txt");
        } catch (Exception e) {
            System.err.println("Database Tidak Ditemukan");
            System.err.println("Silakan Tambah Data Terlebih Dahulu");
            tambahData();
            return;
        }

        /*Kita ambil keyword dari user*/
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("Masukan kata kunci untuk mencari buku : ");
        String cariString = terminalInput.nextLine();

        String [] keywords = cariString.split("\\s+");

        /*Kita cek keywords di database*/
        Utility.cekBukuDiDatabase(keywords, true);

    }

    public static void tambahData() throws IOException {
        FileWriter fileOutput = new FileWriter("database.txt",true);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        /*Mengambil Input dari user*/
        Scanner terminalInput= new Scanner(System.in);
        String penulis, judul, penerbit, tahun;

        System.out.print("Masukan nama penulis : ");
        penulis = terminalInput.nextLine();
        System.out.print("Masukan judul buku : ");
        judul = terminalInput.nextLine();
        System.out.print("Masukan nama penerbit : ");
        penerbit = terminalInput.nextLine();
        System.out.print("Masukan tahun terbit : ");
        tahun = Utility.ambilTahun();

        /*Cek Buku di Database*/
        String keywords [] = {tahun+","+penulis+","+penerbit+","+judul};
        System.out.println(Arrays.toString(keywords));

        boolean isExist = Utility.cekBukuDiDatabase(keywords,false);

        if (!isExist) {
            long nomorEntry = Utility.nomorEntryPerTahun(penulis,tahun)+1;
            String penulisTanpaSpasi = penulis.replaceAll("\\s+","");
            String primaryKey = penulisTanpaSpasi+"_"+tahun+"_"+nomorEntry;
            System.out.println("\nData yang akan Anda masukan adalah");
            System.out.println("---------------------------------------");
            System.out.println("Primary key  : " +primaryKey);
            System.out.println("Tahun Terbit : " +tahun);
            System.out.println("Penulis      : " +penulis);
            System.out.println("Judul        : " +judul);
            System.out.println("Penerbit     : " +penerbit);

            boolean isTambah = Utility.getYesorNo("Apakah Anda akan menambah data tersebut? ");
            if(isTambah) {
                bufferOutput.write(primaryKey + "," + tahun + "," + penulis + "," + penerbit + "," + judul);
                bufferOutput.newLine();
                bufferOutput.flush();
            }
        } else {
            System.out.println("Buku yang akan Anda masukan sudah tersedia di database dengan data berikut : ");
            Utility.cekBukuDiDatabase(keywords,true);
        }


        bufferOutput.close();
        fileOutput.close();
    }

    public static void updateData() throws IOException {
        /*Ambil database original*/
        File database = new File("database.txt");
        FileReader fileReader = new FileReader(database);
        BufferedReader bufferedReader =  new BufferedReader(fileReader);

        /*Buat database sementara*/
        File tempDB = new File("tempDB.txt");
        FileWriter fileWriter = new FileWriter(tempDB);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        /*Tampilkan Data*/
        System.out.println("List Buku");
        tampilkanData();

        /*Ambil user input / pilihan data*/
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\nMasukan nomor buku yang akan diperbaharui : ");
        int updateNum = terminalInput.nextInt();

        /*Tampilkan data yang ingin di update*/
        String data = bufferedReader.readLine();
        int numCounts = 0;

        while (data != null) {
            numCounts++;
            StringTokenizer stringTokenizer = new StringTokenizer(data,",");

            /*Tampilkan numCounts == updateNum*/
            if (updateNum == numCounts) {
                System.out.println("\nData yang ingin anda update adalah : ");
                System.out.println("------------------------------------------");
                System.out.println("Referensi      : " +stringTokenizer.nextToken());
                System.out.println("Tahun          : " +stringTokenizer.nextToken());
                System.out.println("Penulis        : " +stringTokenizer.nextToken());
                System.out.println("Penerbit       : " +stringTokenizer.nextToken());
                System.out.println("Judul          : " +stringTokenizer.nextToken());

                /*Update Data*/

                /*Mengambil input dari User*/
                String[] fieldData = {"Tahun","Penulis","Penerbit","Judul"};
                String[] tempData = new String[4];

                /*Refresh Token*/
                stringTokenizer = new StringTokenizer(data,",");
                String originalData = stringTokenizer.nextToken();

                for(int i=0; i < fieldData.length; i++) {
                    boolean isUpdate = Utility.getYesorNo("Apakah Anda ingin merubah " +fieldData[i]+ "? ");
                    originalData = stringTokenizer.nextToken();


                    if(isUpdate) {
                        if(fieldData[i].equalsIgnoreCase("tahun")){
                            System.out.print("Masukan Tahun baru : ");
                            tempData[i] = Utility.ambilTahun();
                        } else {
                            /*User Input*/
                            terminalInput = new Scanner(System.in);
                            System.out.print("\nMasukan " + fieldData[i] + " baru : ");
                            tempData[i] = terminalInput.nextLine();
                        }
                    } else {
                        tempData[i] = originalData;
                    }
                }
                /*Tampilkan data baru ke layar*/
                stringTokenizer = new StringTokenizer(data,",");
                stringTokenizer.nextToken();

                System.out.println("\nData baru Anda adalah : ");
                System.out.println("------------------------------------------");
                System.out.println("Tahun          : " +stringTokenizer.nextToken()+ " --> " +tempData[0]);
                System.out.println("Penulis        : " +stringTokenizer.nextToken()+ " --> " +tempData[1]);
                System.out.println("Penerbit       : " +stringTokenizer.nextToken()+ " --> " +tempData[2]);
                System.out.println("Judul          : " +stringTokenizer.nextToken()+ " --> " +tempData[3]);

                boolean isUpdate = Utility.getYesorNo("Apakah Anda yakin ingin memperbaharui data tersebut? ");
                if(isUpdate) {
                    /*Cek data baru di database*/
                    boolean isExist = Utility.cekBukuDiDatabase(tempData,false);

                    if(isExist) {
                        System.err.println("Data buku sudah ada di Database!");
                    } else {
                        /*Format data baru ke database*/
                        String tahun = tempData[0];
                        String penulis = tempData[1];
                        String penerbit = tempData[2];
                        String judul = tempData[3];

                        /*Buat Primary Key*/
                        long nomorEntry = Utility.nomorEntryPerTahun(penulis,tahun)+1;
                        String penulisTanpaSpasi = penulis.replaceAll("\\s+","");
                        String primaryKey = penulisTanpaSpasi+"_"+tahun+"_"+nomorEntry;

                        /*Tulis Data ke Database*/
                        bufferedWriter.write(primaryKey + "," + tahun + "," + penulis + "," + penerbit + "," + judul);
                    }
                } else {
                    bufferedWriter.write(data);
                }
            } else {
                /*Copy Data*/
                bufferedWriter.write(data);
            }
            bufferedWriter.newLine();
            data = bufferedReader.readLine();
        }
        /*Menulis data ke file tempDB*/
        bufferedWriter.flush();

        /*Hapus Dulu*/
        fileReader.close();
        bufferedReader.close();
        fileWriter.close();
        bufferedWriter.close();



        /*Menghapus Database original*/
        database.delete();

        /*Rename tempDB.txt ke database.txt*/
        tempDB.renameTo(database);

    }

    public static void deleteData() throws IOException {
        /*Ambil database original*/
        File database = new File("database.txt");
        FileReader fileReader = new FileReader(database);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        /*Buat database sementara*/
        File tempDB = new File("tempDB.txt");
        FileWriter fileWriter = new FileWriter(tempDB);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);


        /*Tampilkan data*/
        System.out.println("List Buku");
        tampilkanData();

        /*Ambil user input untuk mendelete data*/
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\nMasukan nomor buku yang akan dihapus : ");
        int deleteNum = terminalInput.nextInt();

        /*Looping untuk membaca tiap baris dan skip data yang akan didelete*/
        boolean isFound = false;
        int numCounts = 0;
        String data = bufferedReader.readLine();

        while (data != null){
            numCounts++;
            boolean isDelete = false;

            StringTokenizer stringTokenizer = new StringTokenizer(data,",");


            /*Tampilkan data yang ingin dihapus*/
            if(deleteNum == numCounts){
                System.out.println("\nData yang ingin anda hapus adalah : ");
                System.out.println("----------------------------------------");
                System.out.println("Referensi      : " +stringTokenizer.nextToken());
                System.out.println("Tahun          : " +stringTokenizer.nextToken());
                System.out.println("Penulis        : " +stringTokenizer.nextToken());
                System.out.println("Penerbit       : " +stringTokenizer.nextToken());
                System.out.println("Judul          : " +stringTokenizer.nextToken());

                isDelete = Utility.getYesorNo("Apakah Anda yakin akan menghapus buku tersebut? ");
                isFound = true;
            }

            if(isDelete){
                /*Skip pemindahan dari file original ke file sementara*/
                System.out.println("Data berhasil dihapus!");
            } else {
                /*pemindahan dari file original ke file sementara*/
                bufferedWriter.write(data);
                bufferedWriter.newLine();
            }
            data = bufferedReader.readLine();
        }

        if(!isFound){
            System.err.println("Buku tidak ditemukan!");
        }

        /*Meluncurkan data ke file*/
        bufferedWriter.flush();

        /*Close dulu*/
        bufferedWriter.close();
        fileWriter.close();
        bufferedReader.close();
        fileReader.close();

        System.gc();

        /*Delete Original file*/
        database.delete();

        /*Rename file sementara ke database*/
        tempDB.renameTo(database);

    }
}
