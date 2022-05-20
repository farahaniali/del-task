package ie.ali.taskmanager.task;

import ie.ali.taskmanager.user.User;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Task {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    private Boolean checked;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }


}
