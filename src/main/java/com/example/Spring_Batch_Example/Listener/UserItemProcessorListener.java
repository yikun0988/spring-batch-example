package com.example.Spring_Batch_Example.Listener;

import com.example.Spring_Batch_Example.model.User;
import lombok.SneakyThrows;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

import java.io.Writer;

import static java.lang.String.format;

@Component
public class UserItemProcessorListener implements ItemProcessListener<User, String> {
    private Writer errorWriter;

    @Override
    public void beforeProcess(User user) {
        System.out.println("有用");
    }

    @Override
    public void afterProcess(User user, String s) {
        System.out.println("有用");
    }

    @Override
    public void onProcessError(User user, Exception e) {

    }
}
