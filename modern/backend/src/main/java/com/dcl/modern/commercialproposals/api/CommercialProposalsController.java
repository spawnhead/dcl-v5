package com.dcl.modern.commercialproposals.api;

import com.dcl.modern.commercialproposals.application.CommercialProposalsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API for Commercial Proposals list. Legacy: CommercialProposalsAction.
 * docs/screens/commercial_proposals/.
 */
@RestController
@RequestMapping("/api/commercial-proposals")
public class CommercialProposalsController {

    private final CommercialProposalsService service;

    public CommercialProposalsController(CommercialProposalsService service) {
        this.service = service;
    }

    @GetMapping("/lookups")
    public CpLookupsResponse getLookups() {
        return service.getLookups();
    }

    @PostMapping("/data")
    public CpDataResponse postData(@RequestBody CpDataRequest request) {
        return service.getData(request != null ? request : defaultRequest());
    }

    @PostMapping("/page")
    public CpDataResponse postPage(@RequestBody CpPageRequestDto request) {
        return service.getPage(request != null ? request : new CpPageRequestDto("next", 1, null));
    }

    @PostMapping("/cleanAll")
    public CleanAllResponse postCleanAll() {
        return service.cleanAll();
    }

    @PostMapping("/block")
    public void postBlock(@RequestBody CpBlockRequest request) {
        service.block(request);
    }

    @PostMapping("/clone")
    public CpCloneResponse postClone(@RequestBody CpCloneRequest request) {
        return service.clone(request);
    }

    private static CpDataRequest defaultRequest() {
        return new CpDataRequest(
            "", null, null, null, null, "", "", null, null, false, false, 1, 15);
    }
}
