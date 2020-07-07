package com.example.Spring_Batch_Example.Listener;

import com.example.Spring_Batch_Example.model.User;
import lombok.SneakyThrows;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Writer;
import java.util.List;

import static java.lang.String.format;

public class UserItemWriteListener implements ItemWriteListener<User> {

    @Autowired
    private Writer errorWriter;

    @Override
    public void beforeWrite(List<? extends User> items) {
    }

    @Override
    public void afterWrite(List<? extends User> items) {
    }

    @SneakyThrows
    @Override
    public void onWriteError(Exception exception, List<? extends User> items) {
        errorWriter.write(format("%s%n", exception.getMessage()));
        for (User user : items) {
            errorWriter.write(format("Failed writing message id: %s", user.getId()));
        }
    }
}