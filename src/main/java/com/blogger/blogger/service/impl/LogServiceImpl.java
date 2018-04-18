
package com.blogger.blogger.service.impl;

import com.blogger.blogger.service.ILogService;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements ILogService {
    @Override
    public void addLog(Log log) {
        System.out.println("----");
        System.out.println("save log:" + log.toString());
    }

}
