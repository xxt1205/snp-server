import React, {useEffect, useState} from 'react';
import {StudentType} from "../../interface/StudentFieldType";
import {FormControl, FormHelperText, InputLabel, Paper, Select, SelectChangeEvent, Stack, styled} from "@mui/material";
import MenuItem from "@mui/material/MenuItem";
import {Button, Grid} from "@mui/material/";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import Divider from "@mui/material/Divider";
import AppBarComp from "../AppBarComp";
import LessonTextField from "./LessonTextField";
import LessonTextFieldOut from "./LessonTextFieldOut";
import {useNavigate} from "react-router-dom";
import InfoModal from "../student/InfoModal";
import CssBaseline from "@mui/material/CssBaseline";
import ConcentrationField from "./ConcentrationField";
import EyeballPractice from "./EyeballPractice";
import Typography from "@mui/material/Typography";
import FigurePractice from "./FigurePractice";
import SearchTextbook from "./SearchTextbook";
import SendIcon from "@mui/icons-material/Send";
import axios from "axios";
import LoadingButton from "@mui/lab/LoadingButton";

const TextFields = styled(TextField)`
   input::-webkit-outer-spin-button,
    input::-webkit-inner-spin-button {
        -webkit-appearance: none;
    }
    .MuiFormHelperText-root {
  color: #FF0000 !important;
}
`;

type StudentStudyType = {
    dayCount: string
    study: string[],
    dayOfStudy?: DayOfStudyType,
    getDay: number

}
type DayOfStudyType = {
    studentId: string,
    studyId: string,
    studyDetail: string,
    currentStudyCount: string,
    studyType: string,
    lastDate: string,
    studentInfo?: StudentType,
    studyTypeString: string,
}
type LogType = {
    id: string,
    studentId: string,
    StudyDetail: string,
    studyCount: string,
    concentration: string,
    concentrationAnswer: string,
    rapidEyeball: string,
    eyeBallCount: string,
    figureOneClear: number,
    figureOne: number,
    figureTwoClear: number,
    figureTwo: number,
    textBookCode: string,
    intelligibility: string,
    processingTime: number,
    processingMin: number,
    processingSec: number,
    readCount: string,
    memo: string,
    studyType: string,
    dayOfWeek: string
}
type answerType = {
    [answer: string]: string;
}

const LessonRegister: React.FC<StudentStudyType> = ({dayCount, study, dayOfStudy, getDay}) => {
    const navigate = useNavigate();
    const colorType = ['green', 'red'];
    const [color, setColor] = useState(colorType[0]);
    const [day, setDay] = useState(-1);
    const clearForm = {
        id: '',
        studentId: '',
        studyDetail: '',
        studyCount: '',
        concentration: '',
        concentrationAnswer: '',
        rapidEyeball: '',
        eyeBallCount: '',
        figureOneClear: 0,
        figureOne: 0,
        figureTwoClear: 0,
        figureTwo: 0,
        textBookCode: '',
        intelligibility: '',
        processingTime: '',
        processingMin: '',
        processingSec: '',
        readCount: '',
        memo: '',
        studyType: '',
        dayOfWeek: ''
    }
    const [logForm, setLogForm] = useState({
        id: '',
        studentId: '',
        studyDetail: '',
        studyCount: '',
        concentration: '',
        concentrationAnswer: '',
        rapidEyeball: '',
        eyeBallCount: '',
        figureOneClear: 0,
        figureOne: 0,
        figureTwoClear: 0,
        figureTwo: 0,
        textBookCode: '',
        intelligibility: '',
        processingTime: '',
        processingMin: '',
        processingSec: '',
        readCount: '',
        memo: '',
        studyType: '',
        dayOfWeek: ''
    });
    const [dayOfStudyValue, setDayOfStudyValue] = useState<DayOfStudyType>({
        studentId: '',
        studyId: '',
        studyDetail: '',
        currentStudyCount: '',
        studyType: '',
        lastDate: '',
        studentInfo: undefined,
        studyTypeString: '',
    });
    const [isLoading, setIsLoading] = useState(false);
    const [successMes, setSuccessMes] = useState('');
    const [studyValue, setStudyValue] = useState<string[]>([])
    const [studyDetail, setStudyDetail] = useState('');
    const [studentInfo, setStudentInfo] = useState<StudentType>();
    const [grade, setGrade] = useState('');
    const [answerMap, setAnswerMap] = useState<answerType>({
        1: '',
        2: '',
        3: '',
        4: '',
        5: '',
        6: '',
        7: '',
        8: '',
        9: '',
        10: ''
    });
    const clear = {
        1: '',
        2: '',
        3: '',
        4: '',
        5: '',
        6: '',
        7: '',
        8: '',
        9: '',
        10: ''
    };
    const [questionCount, setQuestionCount] = useState(0);

    const onChange = (name: string, value: string) => {
        setLogForm({...logForm, [name]: value})
    }
    const onChangeNumber = (name: string, value: number) => {
        if (name === 'questionCount') {
            setQuestionCount(value);
            return
        }
        setLogForm({...logForm, [name]: value})
    }
    const onChangeAnswer = (name: string, value: string) => {
        setAnswerMap({...answerMap, [name]: value})
    }

    useEffect(() => {

        if (study !== undefined) {
            setStudyValue(study);
            setDay(getDay);
            if (dayOfStudy !== undefined && dayOfStudy.studentInfo !== undefined) {
                const getStudentInfo = dayOfStudy.studentInfo;
                setDayOfStudyValue(dayOfStudy);
                setStudyDetail(dayOfStudy.studyDetail);
                setStudentInfo(getStudentInfo);
                setGrade(getStudentInfo.gradeToString + ' ' + getStudentInfo.gradeLv)
                setLogForm({
                    ...logForm, studyCount: dayOfStudy.currentStudyCount, studentId: getStudentInfo.id,
                    studyDetail: dayOfStudy.studyDetail,studyType: dayOfStudy.studyType
                })
            }
        }
    }, [study, dayOfStudy])
    const fullCount = 10;
    let totalCount = 0;
    useEffect(() => {
        setAnswerMap(clear);
        if (questionCount !== 0) {
            totalCount = (fullCount + questionCount) - fullCount;
            for (let i = fullCount; i > totalCount; i--) {
                delete answerMap[i.toString()];
                setAnswerMap({...answerMap});
            }
        }
    }, [questionCount])
    const onChangeSelect = (e: SelectChangeEvent) => {
        setStudyDetail(e.target.value);
    }

    const onSubmitLog = (e:React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setIsLoading(true)
        let data ={logForm, answerMap}
        axios.post(`/api/lesson/save/${day}`,data)
            .then(()=> {
                setColor(colorType[0]);
                setSuccessMes('저장성공')
            })
            .catch(error => {
                console.log(error)
                setColor(colorType[1]);
                setSuccessMes('저장 실패');

            })
            .finally(() => {
                setIsLoading(false);
            })
        console.log("logForm = " + logForm)
        console.log("answerMap = " + answerMap)
        console.log("day = " + day)

    }

    return (
        <Paper
            sx={{
                p: 2,
                display: 'flex',
                flexDirection: 'column'
            }}
        >
            <CssBaseline/>
            <AppBarComp typography={'수업자료 등록'}/>
            <Box component="form" noValidate onSubmit={onSubmitLog} sx={{mt: 3}}>
                <FormControl component="fieldset" variant="standard">
                    <Grid container spacing={2}>
                        <Grid item xs={12} sx={{marginTop: 2}}>
                            <Stack
                                direction="row"
                                divider={<Divider orientation="vertical" sx={{bgcolor: 'grey'}} flexItem/>}
                                spacing={2}
                            >
                                <LessonTextField widthValue={85} getName={'name'} getValue={studentInfo?.name}
                                                 getLabel={'학생이름'}/>
                                <LessonTextField widthValue={85} getName={'grade'} getValue={grade}
                                                 getLabel={'구분'}/>
                                <LessonTextField widthValue={85} getName={'parentName'}
                                                 getValue={studentInfo?.parentName}
                                                 getLabel={'학부모 이름'}/>
                                <LessonTextField widthValue={130} getName={'parentPhone'}
                                                 getValue={studentInfo?.parentPhone}
                                                 getLabel={'학부모 연락처'}/>
                                <Paper
                                    sx={{
                                        display: 'flex',
                                        flexDirection: 'column'
                                    }}>
                                    <Grid>
                                        <Typography sx={{p: 1, fontSize: 12}}>오늘수업</Typography>
                                        <FormControl size="small" sx={{marginLeft: 1, marginRight: 1, marginBottom: 1}}>
                                            <InputLabel id="day">{dayOfStudyValue.studyTypeString}</InputLabel>
                                            <Select
                                                id="day"
                                                value={studyDetail}
                                                label={dayOfStudyValue.studyTypeString}
                                                onChange={onChangeSelect}
                                                sx={{minWidth: 155, maxWidth: 120}}
                                                style={{marginBottom: 1}}

                                            > <MenuItem disabled={true} value=''>코스선택</MenuItem>
                                                {studyValue.map((item, index) => {
                                                    return <MenuItem key={index} value={item}>{item}</MenuItem>
                                                })}
                                            </Select>
                                        </FormControl>

                                        <LessonTextFieldOut widthValue={90} value={dayOfStudyValue.currentStudyCount}
                                                            name={'currentStudyCount'} label={'일수'}/>
                                    </Grid>
                                </Paper>
                                <InfoModal studentInfo={studentInfo}/>
                            </Stack>
                        </Grid>
                        <Grid item xs={12}>
                            <Stack
                                direction="row"
                                divider={<Divider orientation="vertical" sx={{bgcolor: 'grey',}} flexItem/>}
                                spacing={1}
                            >

                                <ConcentrationField name={'concentration'} getValue={logForm.concentration}
                                                    answer={logForm.concentrationAnswer} onChange={onChange}/>
                                <EyeballPractice getRapidEyeball={logForm.rapidEyeball}
                                                 getEyeBallCount={logForm.eyeBallCount} onChange={onChange}/>
                                <FigurePractice getName={'figureOne'} getClearName={'figureOneClear'} num={1}
                                                onChange={onChangeNumber} getFigure={logForm.figureOne}
                                                getFigureClear={logForm.figureOneClear}/>
                                <FigurePractice getName={'figureTwo'} getClearName={'figureTwoClear'} num={2}
                                                onChange={onChangeNumber} getFigure={logForm.figureTwo}
                                                getFigureClear={logForm.figureTwoClear}/>

                            </Stack>

                        </Grid>
                        <Grid item xs={12}>
                            <Stack
                                direction="row"
                                divider={<Divider orientation="vertical" sx={{bgcolor: 'grey'}} flexItem/>}
                                spacing={2}
                            >

                                <SearchTextbook getScore={answerMap} onChange={onChange} getMin={logForm.processingMin}
                                                getSec={logForm.processingSec}
                                                onChangeNumber={onChangeNumber} onChangeAnswer={onChangeAnswer}/>

                            </Stack>
                        </Grid>
                        <Grid item xs={1} sm={3}>
                            <LoadingButton
                                type="submit"
                                variant="contained"
                                loading={isLoading}

                            >
                                저장
                            </LoadingButton>
                            <FormHelperText sx={{color:{color}}}>{successMes}</FormHelperText>
                        </Grid>
                    </Grid>
                </FormControl>
            </Box>

        </Paper>
    )
        ;
}

export default LessonRegister;