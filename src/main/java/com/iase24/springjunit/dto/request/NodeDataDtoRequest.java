package com.iase24.springjunit.dto.request;

import com.iase24.springjunit.entities.Node;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NodeDataDtoRequest {

    private Long childrenId;
    private Node parentNode;
}
