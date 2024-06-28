package com.example.conversordemonedas;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import com.google.gson.*;

public class Main {
    private static ArrayList<Currency> currencies = new ArrayList<>();
    private static Currency currency;
    private static Scanner scanner = new Scanner(System.in);
    private static double currentValue;
    private static double finalValue;

    public static void main(String[] args) {
        // Setting URL
        String url_str = "https://v6.exchangerate-api.com/v6/958fef286f09caf662658442/latest/USD";

        try {
            // Making Request
            URL url = new URL(url_str);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            // Convert to JSON
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonobj = root.getAsJsonObject();
            
            // Accessing object
            JsonObject req_result = jsonobj.get("conversion_rates").getAsJsonObject();
            Double ARS = req_result.get("ARS").getAsDouble();
            Double BRL = req_result.get("BRL").getAsDouble();
            Double CLP = req_result.get("CLP").getAsDouble();
            
            // Add currencies to the currency array
            currencies.add(new Currency("USD", "ARS", ARS));
            currencies.add(new Currency("ARS", "USD", 1/ARS));
            currencies.add(new Currency("USD", "BRL", BRL));
            currencies.add(new Currency("BRL", "USD", 1/BRL));
            currencies.add(new Currency("USD", "CLP", CLP));
            currencies.add(new Currency("CLP", "USD", 1/CLP));

            greet();

        } catch (IOException e) {
            System.out.println("Something went wrong. Please try again later.");
        } 
		
    }

    private static void greet() {
            try {
                System.out.println("********************************************************");
                System.out.println("Sea bienvenido/a al Conversor de Monedas =]\n");
                
                System.out.println("1) Dólar =>> Peso argentino");
                System.out.println("2) Peso argentino =>> Dólar");
                System.out.println("3) Dólar =>> Real brasileño");
                System.out.println("4) Real brasileño =>> Dólar");
                System.out.println("5) Dólar =>> Peso chileno");
                System.out.println("6) Peso chileno =>> Dólar");
                System.out.println("********************************************************\n");

                System.out.print("Por favor, seleccione una de las opciones anteriores: "); 

                select();
                
            } catch (IndexOutOfBoundsException | InputMismatchException | NumberFormatException e) {
                System.out.print("Por favor escoge uno de los números anteriores: ");
                invalidOption();
            }
    }

    private static void invalidOption() {
        try {          
            select();
        } catch (IndexOutOfBoundsException | InputMismatchException | NumberFormatException e) {
            System.out.print("Por favor escoge uno de los números anteriores: ");
            invalidOption();
        }
    }

    private static void select() {
        String text = scanner.nextLine();
        int i = Integer.parseInt(text);
        currency = currencies.get(i-1);

        try {
            System.out.print("Ingresa el valor que deseas convertir: ");
            convert();
        } catch (NumberFormatException e) {
            System.out.print("Por favor escoge un número valido: ");
            invalidNumber();
        }
    }

    private static void invalidNumber() {
        try {
            convert();
        } catch (NumberFormatException e) {
            System.out.print("Por favor escoge un número valido: ");
            invalidNumber();
        }
    }

    private static void convert() {
        String newText = scanner.nextLine();
        currentValue = Double.parseDouble(newText);

        while (currentValue <= 0) {
            System.out.print("Por favor escoge un valor mayor que 0: ");
            newText = scanner.nextLine();
            currentValue = Double.parseDouble(newText);
        }

        finalValue = currentValue*currency.getRate();
        System.out.println("El valor " + String.format("%.2f", currentValue) + " [" + currency.getInitialCurrency() + "] corresponde al valor final de =>>> " + String.format("%.2f", finalValue) + " [" + currency.getFinalCurrency() + "]\n\n");

        greet();
    }
}
