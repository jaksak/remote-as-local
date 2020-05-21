package pl.longhorn.cglibtests.map;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Position implements Serializable {
    private int x;
    private int y;
}
