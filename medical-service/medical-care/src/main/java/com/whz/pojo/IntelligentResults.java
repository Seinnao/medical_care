package com.whz.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 文辉正
 * @since 2023/4/22 20:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntelligentResults {

    //查询的类型
    private String type;

    private Object data;

}
