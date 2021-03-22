package rest.univer.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonIncorrectData {
    private String info;

    public PersonIncorrectData(String info) {
        this.info = info;
    }
}
