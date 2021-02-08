package sdm.reversi.utils;

import java.io.IOException;

@FunctionalInterface
public interface TriConsumer<A, B, C> {
    void accept (A a, B b, C c) throws IOException, ClassNotFoundException;
}