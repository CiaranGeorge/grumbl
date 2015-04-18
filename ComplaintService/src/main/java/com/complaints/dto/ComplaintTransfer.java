package com.complaints.dto;

import lombok.Getter;
import lombok.Setter;

public class ComplaintTransfer
{
    @Getter @Setter String ownerUsername;
    @Getter @Setter private String companyName;
    @Getter @Setter private String companyType;
    @Getter @Setter private String title;
    @Getter @Setter private String description;
}
