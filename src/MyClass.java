public class MyClass {

    public void publicHello() {
        System.out.println("public: hello");
    }

    @Repeat(times = 1)
    public void publicAnnotated() {
        System.out.println("public: annotated (не должен вызываться)");
    }

    public int publicSum(int a, int b) {
        return a + b;
    }

    @Repeat(times = 2)
    protected void protectedGreet(String name) {
        System.out.println("protected: привет, " + name);
    }

    @Repeat(times = 3)
    protected void protectedPrint(String text, int n) {
        System.out.println("protected: " + text + " #" + n);
    }

    protected int protectedSquare(int x) { // без аннотации: пропускаем
        return x * x;
    }

    @Repeat(times = 2)
    private void privateMultiply(int a, int b) {
        System.out.println("private: " + a + " * " + b + " = " + (a * b));
    }

    @Repeat(times = 4)
    private void privateEcho(String text) {
        System.out.println("private: " + text);
    }

    private void privateSkipped(double x) { // без аннотации: пропускаем
        System.out.println("private: не должен вызываться " + x);
    }
}