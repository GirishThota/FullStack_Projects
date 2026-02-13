# Resume Skill Classifier (Basic AI/NLP Project)

A very simple ML project to demonstrate AI basics:
- Text → numbers using **CountVectorizer**
- Classification using **Logistic Regression**
- Train & save model, then predict on new resume text

## Setup
```bash
python -m venv .venv
# Windows: .venv\Scripts\activate
# macOS/Linux: source .venv/bin/activate
pip install -r requirements.txt
```

## Train
```bash
python src/train.py
```
This trains on `data/training_data.csv` and saves `model.joblib`.

## Predict
```bash
python src/predict.py --text "Java Spring Boot REST APIs JPA MySQL JWT"
```

Or from a text file:
```bash
python src/predict.py --file sample_resume.txt
```

## Notes
- Dataset is small on purpose (easy to understand).
- You can expand `data/training_data.csv` with more examples to improve accuracy.
