import { TestBed, inject } from '@angular/core/testing';

import { UserColorService } from './user-color.service';

describe('UserColorService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [UserColorService]
    });
  });

  it('should be created', inject([UserColorService], (service: UserColorService) => {
    expect(service).toBeTruthy();
  }));
});
