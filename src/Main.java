import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>(); //Выбрана коллекция потому, что в мапе нельзя хранить одинаковые значения
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        // ЗАДАЧА №1, Найти количество несовершеннолетних (т.е. людей младше 18 лет).
        long findMinors = persons.stream()
                .limit(500) //сокращаем список до 500 из 10_000_000
                .filter((o1) -> o1.getAge() < 18)
                .count();
        System.out.println("Количество несовершеннолетних: " + findMinors);

        // ЗАДАЧА №1, Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет).
        List<String> findRecruits = persons.stream()
                .limit(500) //сокращаем список до 500 из 10_000_000
                .filter((o1) -> o1.getAge() >= 18 && o1.getAge() <= 27 && o1.getSex() == Sex.MAN) //мужчин от 18 и до 27 лет
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println("Призывники: \n" + findRecruits);

        // ЗАДАЧА №1, Получить отсортированный по фамилии список потенциально работоспособных
        // людей с высшим образованием в выборке (т.е. людей с высшим образованием от 18 до 60
        // лет для женщин и до 65 лет для мужчин).
        List<Person> findWorkers = persons.stream()
                .limit(500) //сокращаем список до 500 из 10_000_000
                .filter((o1) -> o1.getAge() >= 18 && o1.getEducation().toString().equals("HIGHER"))  // совершеннолетн-ий(яя) с высшим образованием
                .filter((o1) -> o1.getSex() == Sex.MAN && o1.getAge() > 65 || o1.getSex() == Sex.WOMAN && o1.getAge() > 60) // от 18 до 65 лет для мужчин и от 18 до 60 лет для женщин
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        System.out.println("Рабочий класс: \n " + findWorkers);
    }
}
