package com.hitit.sample.github.export;

import com.hitit.sample.github.model.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileExporter {
    void export(File output, List<Repository> repositories) throws IOException;
}
