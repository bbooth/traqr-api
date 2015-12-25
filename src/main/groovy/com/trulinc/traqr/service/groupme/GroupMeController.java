package com.trulinc.traqr.service.groupme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/groupme")
public class GroupMeController {

    @Autowired
    GroupMeQonqrService groupMeQonqrService;

    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    @ResponseBody
    public void callback(@RequestBody GroupMeCallBack groupMeCallBack) {
        groupMeQonqrService.handleCallback(groupMeCallBack);
    }

}
