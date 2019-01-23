package com.devworker.kms.controller.acl;

import com.devworker.kms.dto.acl.AclDto;
import com.devworker.kms.service.acl.AclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class AclController {
    @Autowired
    AclService aclService;

    @PutMapping("/acl")
    public String addAcl(@RequestBody AclDto dto) {
        return aclService.addAcl(dto);
    }

    @PostMapping("/acl")
    public void updateAcl(@RequestBody AclDto dto){
        aclService.updateAcl(dto);
    }

    @DeleteMapping("/acl/{aclId}")
    public void deleteAcl(@PathVariable String aclId) {
        aclService.deleteAcl(aclId);
    }

    @GetMapping("/acl/{aclId}")
    public AclDto getAcl(@PathVariable String aclId) {
        return aclService.getAcl(aclId);
    }

    @GetMapping("/acl")
    public Page<AclDto> getAclList(Pageable pageable) {
        return aclService.getAclPage(pageable);
    }
}
