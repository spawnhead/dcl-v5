export interface LookupItemDto {
  id: string;
  name: string;
}

export interface CpDataRequest {
  cprNumber?: string;
  department?: LookupItemDto | null;
  contractor?: LookupItemDto | null;
  user?: LookupItemDto | null;
  stuffCategory?: LookupItemDto | null;
  cprDateFrom?: string;
  cprDateTo?: string;
  cprSumFrom?: number | null;
  cprSumTo?: number | null;
  cprProposalReceivedFlag?: boolean;
  cprProposalDeclined?: boolean;
  page?: number;
  pageSize?: number;
}

export interface CpRowDto {
  cprId: string;
  cprNumber: string;
  cprDate: string;
  cprContractor: string;
  cprSumFormatted: string;
  cprCurrency: string;
  cprStfName: string;
  reservedState: string;
  cprBlock: string;
  cprUser: string;
  cprDepartment: string;
  cprCheckPrice: string;
}

export interface CpDataResponse {
  items: CpRowDto[];
  page: number;
  pageSize: number;
  hasNextPage: boolean;
  sort?: { field: string; direction: string }[];
}

export interface CpDefaultsDto {
  cprNumber?: string;
  department?: LookupItemDto | null;
  contractor?: LookupItemDto | null;
  user?: LookupItemDto | null;
  stuffCategory?: LookupItemDto | null;
  cprDateFrom?: string;
  cprDateTo?: string;
  cprSumFrom?: number | null;
  cprSumTo?: number | null;
  cprProposalReceivedFlag?: boolean;
  cprProposalDeclined?: boolean;
}

export interface CpLookupsResponse {
  defaults: CpDefaultsDto;
  lookups: {
    departments: LookupItemDto[];
    contractors: LookupItemDto[];
    users: LookupItemDto[];
    stuffCategories: LookupItemDto[];
  };
}
