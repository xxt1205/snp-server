import React, {useState} from 'react';
import {CssBaseline, FormHelperText, Grid, styled, TextField, ThemeProvider, Typography} from "@mui/material";
import ChangeProps from "../interface/ChangeProps";

const FormHelperTexts = styled(FormHelperText)`
  width: 100%;
  padding-left: 16px;
  font-weight: 700 !important;
  color: #d32f2f !important;
`;
const TextFields = styled(TextField)`
   input::-webkit-outer-spin-button,
    input::-webkit-inner-spin-button {
        -webkit-appearance: none;
    }
`;
type TextFieldsCpt = {
    onChange: (e:ChangeProps) => void,
    studentValue: string
    textType: string
    labelType: string
}

const TextFieldsCpt:React.FC<TextFieldsCpt> = ({onChange, studentValue,textType,labelType}) => {
    return(
        <Grid item xs={6}>
            <TextFields
                required
                fullWidth
                type="text"
                id={textType}
                name={textType}
                label={labelType}
                value={studentValue}
                onChange={onChange}
                // error={pwError!== ''}
            />
            <FormHelperTexts></FormHelperTexts>
        </Grid>
    )
}
export default TextFieldsCpt;