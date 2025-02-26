package org.example.usermanagement.dtos;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private String status;
    private UserDTO createdBy;
    private List<DishDTO> items;
    private String scheduledFor;
    private String createdDate;

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", createdBy=" + createdBy +
                ", items=" + items +
                ", scheduledFor='" + scheduledFor + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}

