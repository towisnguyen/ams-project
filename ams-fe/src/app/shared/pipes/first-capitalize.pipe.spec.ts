import { FirstCapitalizePipe } from './first-capitalize.pipe';

describe('FirstCapitalizePipe', () => {
  it('create an instance', () => {
    const pipe = new FirstCapitalizePipe();
    expect(pipe).toBeTruthy();
  });
});
