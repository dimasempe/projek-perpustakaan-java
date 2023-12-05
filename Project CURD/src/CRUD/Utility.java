package CRUD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Year;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Utility {

    static boolean cekBukuDiDatabase(String[] keywords, boolean isDisplay) throws IOException {
        FileReader fileInput = new FileReader("database.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        String data = bufferInput.readLine();
        boolean isExist = false;
        int nomorData = 0;
        if(isDisplay) {
            System.out.println("\n| No |\tTahun |\tPenulis             |\tPenerbit             |\tJudul Buku");
            System.out.println("-------------------------------------------------------------------------------");
        }
        while (data != null){
            /*Cek Keywords di dalam baris*/
            isExist = true;
//            System.out.println(data);
//            System.out.println(Arrays.toString(keywords));
            for(String keyword: keywords){
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }
//            System.out.println(isExist);


            if (isExist){
                if(isDisplay) {
                    nomorData++;
                    StringTokenizer stringToken = new StringTokenizer(data, ",");

                    stringToken.nextToken();
                    System.out.printf("| %s  ", nomorData);
                    System.out.printf("|\t%s  ", stringToken.nextToken());
                    System.out.printf("|\t%-20s", stringToken.nextToken());
                    System.out.printf("|\t%-20s ", stringToken.nextToken());
                    System.out.printf("|\t%s", stringToken.nextToken());
                    System.out.println("\n");
                } else {
                    break;
                }
            }

            data = bufferInput.readLine();
        }
        if(isDisplay) {
            System.out.println("-------------------------------------------------------------------------------");
        }
        bufferInput.close();
        fileInput.close();

        return isExist;
    }

    protected static long nomorEntryPerTahun(String penulis, String tahun) throws IOException {
        FileReader fileInput = new FileReader("database.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        long entry = 0;
        String data = bufferInput.readLine();
        Scanner dataScanner;
        String primaryKey;

        while (data != null) {
            dataScanner = new Scanner(data);
            dataScanner.useDelimiter(",");
            primaryKey = dataScanner.next();
            dataScanner = new Scanner(primaryKey);
            dataScanner.useDelimiter("_");

            penulis = penulis.replaceAll("\\s+","");
            if(penulis.equalsIgnoreCase(dataScanner.next()) && tahun.equalsIgnoreCase(dataScanner.next())) {
                entry = dataScanner.nextInt();
            }

            data = bufferInput.readLine();
        }

        bufferInput.close();
        fileInput.close();
        return entry;
    }

    static String ambilTahun() throws IOException {
        Scanner terminalInput= new Scanner(System.in);
        String tahun = terminalInput.nextLine();
        boolean tahunValid = true;
        while (tahunValid) {
            try {
                Year.parse(tahun);
                tahunValid = false;
            } catch (Exception e) {
                System.out.println("Format tahun yang anda masukan salah! Format = YYYY");
                System.out.print("Masukan tahun lagi : ");
                tahunValid = true;
                tahun = terminalInput.nextLine();
            }

        } return tahun;
    }

    public static boolean getYesorNo(String message) {
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\n" +message+ "(y/n) : ");
        String pilihanUser = terminalInput.next();

        while ((!pilihanUser.equalsIgnoreCase("y")) && (!pilihanUser.equalsIgnoreCase("n"))) {
            System.err.println("Pilihan Anda bukan y atau n, silakan pilih kembali");
            System.out.print("\n" +message+ " (y/n) : ");
            pilihanUser = terminalInput.next();
        }

        return pilihanUser.equalsIgnoreCase("y");
    }

    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            if (System.getProperty("os.name").contains("Windows")){
            } else {
                System.out.println("\033\143");
            }
        } catch (Exception ex){
            System.err.println("Tidak Bisa Clear Screen");
        }
    }
}
