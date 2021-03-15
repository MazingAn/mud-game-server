package com.mud.game.feign.FeignService;

import com.mud.game.feign.IRecordFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecordService<F, S> {
    public static IRecordFeign recordFeign;

    @Autowired
    public void setWeixinFeign(IRecordFeign recordFeign) {
        RecordService.recordFeign = recordFeign;
    }
}
