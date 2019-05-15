package CountDownLatch;

/**
 * @author 叶俊晖
 * @date 2019/5/15 0015 21:15
 */
public enum Country {
    ONE(1,"齐"),TWO(2,"楚"),THREE(3,"燕"),FOUR(4,"赵"),FIVE(5,"魏"),SIX(6,"韩");

    private int id;
    private String name;

    Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Country getCountry(int index){
        Country[] countries = Country.values();
        for (Country country:
             countries) {
            if (country.getId()==index){
                return country;
            }
        }
        return null;
    }
}
