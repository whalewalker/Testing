package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Progress {
    private final int completed;
    private final int toRead;
    private final int inProgress;
}
