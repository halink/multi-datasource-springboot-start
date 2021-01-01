package com.halink.scaffold.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="com-halink-scaffold-common-entity-MiPro")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MiPro {
    @ApiModelProperty(value="")
    private Long id;

    @ApiModelProperty(value="")
    private String code;

    @ApiModelProperty(value="")
    private String name;
}