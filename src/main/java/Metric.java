public class Metric {

    private String name;
    private Long start;

    public Metric(String name) {
        this.name = name;
        this.start = System.nanoTime();
    }

    public void printResultTime() {
        System.out.println();
        Long end = System.nanoTime();
        System.out.println(name + " took " + (end-start) + " ns.");
    }
}
