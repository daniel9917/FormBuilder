package com.tourism.baseapplication.domain;

import com.tourism.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "application")
public class Application extends BaseEntity {

    @NotNull
    @Id
    private UUID id;

    private static final long serialVersionUID = -6522673672423115034L;
    @Size(max = 10)
    private String name;
    private boolean application;

    public Application(String name, boolean application) {
        this.name = name;
        this.application = application;
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
