package br.com.hyper.dtos.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CartRequestDTO {

    Long id;

    String email;
}
