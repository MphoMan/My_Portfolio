import { TestBed } from '@angular/core/testing';

import { BasicAutheHttpInterceptorServiceService } from './basic-authe-http-interceptor-service.service';

describe('BasicAutheHttpInterceptorServiceService', () => {
  let service: BasicAutheHttpInterceptorServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BasicAutheHttpInterceptorServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
