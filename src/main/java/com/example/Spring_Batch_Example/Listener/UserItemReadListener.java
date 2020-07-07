package com.example.Spring_Batch_Example.Listener;

import com.example.Spring_Batch_Example.model.User;
import lombok.SneakyThrows;
import org.springframework.batch.core.ItemReadListener;

import java.io.Writer;

import static java.lang.String.format;

public class UserItemReadListener implements ItemReadListener<User> {
    private Writer errorWriter;

    public UserItemReadListener(Writer errorWriter) {
        this.errorWriter = errorWriter;
    }

    @Override
    public void beforeRead() {
        System.out.println("ItemReadListener-beforeRead 有用");
    }

    @Override
    public void afterRead(User user) {
        System.out.println("ItemReadListener-afterRead 有用");
    }

    @SneakyThrows
    @Override
    public void onReadError(Exception ex) {
        errorWriter.write(format("%s%n", ex.getMessage()));
    }
}
