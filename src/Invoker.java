import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;

public class Invoker {

    public static void main(String[] args) throws Exception {
        MyClass obj = new MyClass();

        Method[] methods = MyClass.class.getDeclaredMethods();
        Arrays.sort(methods, Comparator.comparing(Method::getName));

        for (Method m : methods) {
            int mod = m.getModifiers();
            boolean restricted = Modifier.isProtected(mod) || Modifier.isPrivate(mod);

            if (!restricted || !m.isAnnotationPresent(Repeat.class)) {
                continue;
            }

            int times = m.getAnnotation(Repeat.class).times();
            m.setAccessible(true);
            Object[] callArgs = buildArgs(m);

            System.out.println("== " + m.getName() + " x" + times + " ==");
            for (int i = 0; i < times; i++) {
                Object result = m.invoke(obj, callArgs);
                if (m.getReturnType() != void.class) {
                    System.out.println("результат: " + result);
                }
            }
        }
    }

    private static Object[] buildArgs(Method m) {
        Class<?>[] types = m.getParameterTypes();
        Object[] args = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            if (types[i] == int.class) {
                args[i] = 5;
            } else if (types[i] == String.class) {
                args[i] = "test";
            } else if (types[i] == double.class) {
                args[i] = 2.5;
            } else {
                throw new IllegalArgumentException("Неподдерживаемый тип: " + types[i]);
            }
        }
        return args;
    }
}