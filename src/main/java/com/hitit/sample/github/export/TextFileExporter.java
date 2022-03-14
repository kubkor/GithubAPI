package com.hitit.sample.github.export;

import com.hitit.sample.github.model.Repository;
import com.hitit.sample.github.model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class TextFileExporter implements FileExporter {
    @Override
    public void export(File output, List<Repository> repositories) throws IOException {
        FileWriter fileWriter = new FileWriter(output);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        for (Repository repository : repositories) {
            for (User user : repository.getUsers()) {
                printWriter.printf("repo: %s, ", repository.getName());
                printWriter.printf("user: %s, ", user.getUserName());
                printWriter.printf("location: %s, ", user.getLocation());
                printWriter.printf("company: %s, ", user.getCompany());
                printWriter.printf("contributions: %d, ", repository.getForkQuantity());
                printWriter.println();
            }
        }

        printWriter.close();
    }

}
