package com.pereira.contacorrente;

import com.pereira.contacorrente.thread.ThreadPost;

import java.util.ArrayList;
import java.util.List;

public class StressTest {

    public static void main(String[] args) {
        List<Thread> threads = criarThreads(3, 20);
        threads.stream().forEach(thread -> thread.start());
    }

    public static List<Thread> criarThreads(Integer qtdThreads, Integer qtdRequisicoes) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < qtdThreads; i++) {
            threads.add(new Thread(new ThreadPost(i, qtdRequisicoes)));
        }
        return threads;
    }

}
