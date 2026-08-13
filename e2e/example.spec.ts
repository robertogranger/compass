// e2e/homepage.spec.ts
import { test, expect } from '@playwright/test';

test('homepage loads successfully', async ({ page }) => {
  await page.goto('/');
  await expect(page).toHaveURL('http://localhost:3000/');
});