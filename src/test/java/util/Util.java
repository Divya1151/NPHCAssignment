import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class Util {

    public static Object readFile(String filePath) {
        JSONParser jsonParser = new JSONParser();
        Object obj = null;
        try (FileReader reader = new FileReader(filePath)) {
            //Read JSON file
            obj = jsonParser.parse(reader);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static long countLines(String filePath){
        Stream<String> stream = null;
        try {
            stream = Files.lines(Path.of(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream.count();
    }

    public static int calculateAge(String date) {
        LocalDate today = LocalDate.now();                          //Today's date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        LocalDate birthday = LocalDate.parse(date, formatter);
        Period p = Period.between(birthday, today);
        System.out.println(p.getYears());
        return p.getYears();

    }

    public static float calculateVariable(int age) {
        if (age <= 18) return 1.0F;
        else if (age <= 35) return 0.8F;
        else if (age <= 50) return 0.5F;
        else if (age <= 75) return 0.367F;
        else return 0.05F;

    }

    public static int genderBonus(String gender) {
        if (gender.equals("M")) return 0;
        else return 500;
    }

    public static String truncateTo2Decimal(float number) {
        System.out.println("Number before Truncate  "+number);
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("Truncated decimal  " + df.format(number));
        return df.format(number);
    }

    public static String calculateTaxRelief(float salary, float taxPaid, String date, String gender) {
        float variable = calculateVariable(calculateAge(date));
        int genderBonus = genderBonus(gender);
        System.out.println("salary  "+salary);
        System.out.println("taxpaid  "+taxPaid);
        System.out.println("variable  "+variable);
        System.out.println("genderbonus  "+genderBonus);
        float calculatedTax = ((salary - taxPaid) * variable) + genderBonus;
        System.out.println("taxrelief  "+calculatedTax);

        String taxRelief = truncateTo2Decimal(((salary - taxPaid) * variable) + genderBonus);
        if (Float.parseFloat(taxRelief) < 50.00) return "50.00";
        else return taxRelief;

    }
}
