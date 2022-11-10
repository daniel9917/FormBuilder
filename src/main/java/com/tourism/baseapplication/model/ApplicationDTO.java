package com.tourism.baseapplication.model;

import com.tourism.model.PersistentDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Data
@EqualsAndHashCode
@Getter
@Setter
public class ApplicationDTO extends PersistentDTO {

    private static final long serialVersionUID = 7188885163550721335L;
    private String name;
    private boolean application;

    public ApplicationDTO(String name, boolean application) {
        this.name = name;
        this.application = application;
    }

    public ApplicationDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isApplication() {
        return application;
    }

    public void setApplication(boolean application) {
        this.application = application;
    }
}
