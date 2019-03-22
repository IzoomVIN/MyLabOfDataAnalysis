package ru.SourceFiles;

public class SuicideStatisticsRow {
    private String country, sex, age;
    private int year, suicidesCount, population;
    private double suicidesTo100KPopulation;

    SuicideStatisticsRow(String country, int year, String sex, String age, int suicidesCount,
                         int population, double suicidesTo100KPopulation ){
        this.country = country;
        this.year = year;
        this.sex = sex;
        this.age = age;
        this.suicidesCount = suicidesCount;
        this.population = population;
        this.suicidesTo100KPopulation = suicidesTo100KPopulation;
    }

    // country
    public String getCountry(){
        return this.country;
    }

    // year
    public int getYear() {
        return year;
    }

    // sex
    public String getSex() {
        return sex;
    }

    // age
    public String getAge() {
        return age;
    }

    // suicidesCount
    public int getSuicidesCount() {
        return suicidesCount;
    }

    // population
    public int getPopulation() {
        return population;
    }

    // suicidesTo100KPopulation
    public double getSuicidesTo100KPopulation() {
        return suicidesTo100KPopulation;
    }

    public boolean equals(SuicideStatisticsRow other){
        return (this.country.equals(other.country)) &&
                (this.year == other.year) &&
                (this.sex.equals(other.sex)) &&
                (this.age.equals(other.age));
    }
}
