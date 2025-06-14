import React from 'react';
import { type RenderBodyArgs } from 'gatsby';

export const onRenderBody = ({ setHtmlAttributes }: RenderBodyArgs) => {
  setHtmlAttributes({ lang: 'en' });
}; 