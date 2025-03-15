package com.iase24.springjunit.dto.request;

import com.iase24.springjunit.dto.ParentNodeDataDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChildrenCategoryToParentDataDtoRequest {

    private Long childrenId;

    private ParentNodeDataDto parentId;
}
